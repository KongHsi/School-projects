#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>

#include "implicit.h"
#define TRUE 1
#define FALSE 0
#define LLONG_MAX  9223372036854775807LL
typedef int bool;

/*
* Determine whether or not a block is in use.
*/
static int block_is_in_use(char *block_start)
{
	return 1 & *((long *)block_start);
}

/*
* Return the size of a block.
*/
static long get_block_size(char *block_start)
{
	return -8 & *((long *)block_start);
}

/*
* Set the size of a block, and whether or not it is in use. Remember each block
* has two copies of the header (one at each end).
*/
static void set_block_header(char *block_start, long block_size, int in_use)
{
	long header_value = block_size | in_use;
	*((long *)block_start) = header_value;
	*((long *)(block_start + (block_size - sizeof(long)) / sizeof(char))) = header_value;
}

/*
* Create a heap that is "size" bytes large.
*/
heap *heap_create(long size, int search_alg)
{
	heap *h = malloc(sizeof(heap));

	h->size = size;
	h->start = malloc(size);
	h->search_alg = search_alg;

	h->next = h->start;
	set_block_header(h->start, size, 0);
	return h;
}

/*
* Dispose of (free) the whole heap.
*/
void heap_dispose(heap *h)
{
	free(h->start);
	free(h);
}

/*
* Find the start of the block, given a pointer to the payload.
*/
static char *get_block_start(char *payload)
{
	return payload - sizeof(long) / sizeof(char);
}

/*
* Find the payload, given a pointer to the start of the block.
*/
static char *get_payload(char *block_start)
{
	return block_start + sizeof(long) / sizeof(char);
}

/*
* Find the start of the next block.
*/
static char *get_next_block(char *block_start)
{
	return block_start + get_block_size(block_start) / sizeof(char);
}


/*
 * Find the start of the previous block.
 */
static char *get_previous_block(char *block_start)
{
	char* previousblock = block_start - sizeof(long)/sizeof(char);//get the footer of the previous block.
	previousblock -= get_block_size(previousblock)/sizeof(char) - sizeof(long)/sizeof(char);//subtract (the size of the previous block minus size of a header).
	return previousblock;
}

/*
 * Coalesce two consecutive free blocks. Return a pointer to the beginning
 * of the coalesced block.
 */
static char *coalesce(char *first_block_start, char *second_block_start)
{
	set_block_header(first_block_start, get_block_size(first_block_start)/sizeof(char) + get_block_size(second_block_start)/sizeof(char), 0);
	
	return first_block_start;
}

/*
 * Determine whether or not the given block is at the front of the heap.
 */
static int is_first_block(heap *h, char *block_start)
{
    return block_start == h->start;
}

/*
 * Determine whether or not the given block is at the end of the heap.
 */
static int is_last_block(heap *h, char *block_start)
{
    return block_start + get_block_size(block_start) == h->start + h->size;
}

/*
 * Print the structure of the heap to the screen.
 */
void heap_print(heap *h)
{
	char* currentBlock = h->start;

	//flag for if the current block is the last block.
	bool lastBlock = FALSE;
	while (TRUE) {
		printf("Block at address %ld\n", (long*)currentBlock);
		printf("    Size: %ld\n", get_block_size(currentBlock));
		if (block_is_in_use(currentBlock))
			printf("    In use: Yes\n");
		else
			printf("    In use: No\n");

		currentBlock = get_next_block(currentBlock);
		//break if the currentblock is the last block.
		if (lastBlock)
			break;
		//loop again to print out the information of the last block.
		if (is_last_block(h, currentBlock))
			lastBlock = TRUE;
	}


}

/*
 * Determine the average size of a free block.
 */
long heap_find_avg_free_block_size(heap *h)
{
	char* currentBlock = h->start;
	long totalSize = 0;
	long numOfFreeBlock=0;
	bool lastBlock = FALSE;
	while (TRUE) {
		//if currentblock is free, then add the size of the block and increase the count.
		if (block_is_in_use(currentBlock)==0) {
			totalSize += get_block_size(currentBlock);
			numOfFreeBlock++;
		}

		currentBlock = get_next_block(currentBlock);
		if (lastBlock)
			break;
		if (is_last_block(h, currentBlock))
			lastBlock = TRUE;
	}
	//average = totalsize/total num of blocks
	return (totalSize / numOfFreeBlock);
}

/*
 * Free a block on the heap h. Beware of the case where the  heap uses
 * a next fit search strategy, and h->next is pointing to a block that
 * is to be coalesced.
 */
void heap_free(heap *h, char *payload)
{
	char* currentBlock = get_block_start(payload);
	char* previousBlock;
	char* nextBlock;
	char* freeBlock;
	//get the currentblock size
	long size = get_block_size(currentBlock);
	//if the currentblock is not the last block, check the next block.
	if (is_last_block(h, currentBlock) == 0) {
		nextBlock = get_next_block(currentBlock);
		if (block_is_in_use(nextBlock) == 0)
			size += get_block_size(nextBlock);
	}
	//if the currentblock is not the first block, check the previous block.
	if (is_first_block(h, currentBlock) == 0) {
		previousBlock = get_previous_block(currentBlock);
		if ((block_is_in_use(previousBlock) == 0)) {
			//add the size of the previous block, and let the start of the previous block be the start of the newly freed block.
			size += get_block_size(previousBlock);
			freeBlock = previousBlock;
		}
	}
	else
		freeBlock = currentBlock;
	//if h->next is at the middle of the new block, then move h->next to the head of the newly freed block.
	if(h->next == nextBlock || h->next == currentBlock)
		h->next = freeBlock;
	
	set_block_header(freeBlock, size, 0);
}

/*
 * Determine the size of the block we need to allocate given the size
 * the user requested. Don't forget we need space for the header  and
 * footer.
 */
static long get_size_to_allocate(long user_size)
{
	//size needed is payload + 2 * header.
	long size = (user_size + 2 * (sizeof(long) / sizeof(char)));
	//block size needs to be multiple of 8.
	for (long n = 1; n < LLONG_MAX; n++) {
		if (sizeof(long) * n >= size)
			return (sizeof(long) * n);
	}
}

/*
 * Turn a free block into one the user can utilize. Split the block if
 * it's more than twice as large or MAX_UNUSED_BYTES bytes larger than
 * needed.
 */
static void *prepare_block_for_use(char *block_start, long real_size)
{
	long initialSize = get_block_size(block_start);
	long size = real_size;

	//split if needed.
	if ((initialSize > (2 * size)) || (initialSize > (size + MAX_UNUSED_BYTES))) {
		set_block_header(block_start, size, 1);//set block use to 1.
		set_block_header(get_next_block(block_start), initialSize - size, 0);
	}
	else {
		set_block_header(block_start, initialSize, 1);
	}
	return get_payload(block_start);
}

/*
 * Malloc a block on the heap h, using first fit. Return NULL if no block
 * large enough to satisfy the request exits.
 */
static void *malloc_first_fit(heap *h, long user_size)
{
	bool lastBlock = FALSE;
	long blockSize = get_size_to_allocate(user_size);
	char* currentBlock = h->start;
	while (TRUE) {
		//if the currentblock is free and has enough size.
		if ((block_is_in_use(currentBlock) == 0) && (get_block_size(currentBlock) >= blockSize)) {
			h->next = currentBlock;
			return prepare_block_for_use(currentBlock, blockSize);
		}
		currentBlock = get_next_block(currentBlock);
		if (lastBlock)
			break;
		if (is_last_block(h, currentBlock))
			lastBlock = TRUE;
	}
	return NULL;
}

/*
 * Malloc a block on the heap h, using best fit. Return NULL if no block
 * large enough to satisfy the request exits.
 */
static void *malloc_best_fit(heap *h, long user_size)
{
	long blockSize = get_size_to_allocate(user_size);
	char* currentBlock = h->start;

	char* idealBlock =NULL;
	long idealBlockSize = LLONG_MAX;
	
	bool lastBlock = FALSE;
	while (TRUE) {
		if ((block_is_in_use(currentBlock) == 0)&& (get_block_size(currentBlock) >= blockSize)) {
			//check if this block has a closer size to what we want.
			if (get_block_size(currentBlock) < idealBlockSize) {
				idealBlock = currentBlock;
				idealBlockSize = get_block_size(currentBlock);
			}

		}
		currentBlock = get_next_block(currentBlock);
		if (lastBlock)
			break;
		if (is_last_block(h, currentBlock))
			lastBlock = TRUE;
	}
	if (idealBlock != NULL) {
		return prepare_block_for_use(idealBlock, blockSize);
	}
	
    return idealBlock;
}

/*
 * Malloc a block on the heap h, using next fit. Return NULL if no block
 * large enough to satisfy the request exits.
 */
static void *malloc_next_fit(heap *h, long user_size)
{
	bool lastBlock = FALSE;
	bool flag = TRUE;
	long blockSize = get_size_to_allocate(user_size);
	char* currentBlock = h->next;
	while (TRUE) {
		//if the currentblock is free and has enough size.
		if ((block_is_in_use(currentBlock) == 0) && (get_block_size(currentBlock) >= blockSize)) {
			h->next = currentBlock;
			return prepare_block_for_use(currentBlock, blockSize);
		}
		currentBlock = get_next_block(currentBlock);
		if (lastBlock) {
			//if it reaches the end of the heap, loop again from the beginning of the heap.
			currentBlock = malloc_first_fit(h, user_size);
			break;
		}
		if (is_last_block(h, currentBlock)) 
			lastBlock = TRUE;
	}
	return currentBlock;
}

/*
* Our implementation of malloc.
*/
void *heap_malloc(heap *h, long size)
{
	switch (h->search_alg)
	{
	case HEAP_FIRSTFIT: return malloc_first_fit(h, size);
	case HEAP_NEXTFIT: return malloc_next_fit(h, size);
	case HEAP_BESTFIT: return malloc_best_fit(h, size);
	}
	return NULL;
}


