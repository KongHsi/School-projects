#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
#include <sys/time.h>
#include "implicit.h"

/*
* Set to 1 for additional debugging statements.
*/
#define DEBUG 0

/*
* Default size for the heap is 1MB.
*/
#define HEAP_SIZE 1048576

/*
* Default size for array holding pointers returned by malloc.
*/
#define MAX_POINTERS 1000

/*
* Function use to initialize a heap to some known state, to be
* used when testing your heap_print() function only.
*/
void initialize_test_heap(heap *h)
{
	*((long *)h->start) = 97;
	*((long *)(h->start + 96)) = 65;
	*((long *)(h->start + 160)) = 80;
	*((long *)(h->start + 240)) = 33;
	*((long *)(h->start + 272)) = 88;
	*((long *)(h->start + 360)) = 56;
	*((long *)(h->start + 416)) = 201;
	*((long *)(h->start + 616)) = h->size - 616;
}

/*
* Function to initialize the random number generator.
*/
void initialize_rng()
{
	struct timeval t1;
	gettimeofday(&t1, NULL);
	srand(t1.tv_usec * t1.tv_sec);
}

/*
* Function to pick a rand size for a new block.
*/
long get_rand_block_size()
{
	long size = 4;
	while (size < 512 && rand() % 6 != 0)
	{
		size <<= 1;
	}
	while (size < 2048 && rand() % 2 != 0)
	{
		size <<= 1;
	}
	return size + rand() % size;
}

/*
* Function that performs a large number of heap operations. Returns the
* average size of a free block.
*/
long test_heap(int search_alg, int op_count)
{
	heap *h = heap_create(HEAP_SIZE, search_alg);
	char *pointer_array[MAX_POINTERS];
	long fragmentation, size;
	int nb_pointers = 0;
	char *new_pointer;
	int index;

	while (op_count-- > 0)
	{
		if ((nb_pointers == 0) || (rand() % MAX_POINTERS > nb_pointers))
		{
			size = get_rand_block_size();
#if DEBUG
			printf("Malloc'ing %ld bytes.\n", size);
#endif
			new_pointer = heap_malloc(h, size);
			if (new_pointer == NULL)
			{
				printf("Ran out of memory with %d operations left.\n", op_count);
				break;
			}
			pointer_array[nb_pointers++] = new_pointer;
		}
		else
		{
			index = rand() % nb_pointers;
#if DEBUG
			printf("Freeing pointer at address %" PRIxPTR "\n", (intptr_t)pointer_array[index]);
#endif
			heap_free(h, pointer_array[index]);
			pointer_array[index] = pointer_array[--nb_pointers];
		}
#if DEBUG
		heap_print(h);
#endif
	}
	fragmentation = heap_find_avg_free_block_size(h);
	heap_dispose(h);
	return fragmentation;
}

/*
* Main function.
*/
int main(int argc, char *argv[])
{
	initialize_rng();
	/*
	* First make sure we can print the heap correctly.
	*/
	heap *h = heap_create(HEAP_SIZE, HEAP_FIRSTFIT);
	initialize_test_heap(h);
	heap_print(h);
	
	heap_dispose(h);
	putchar('\n');

	/*
	* Now run tests on all three types of search algorithm.
	*/
	printf("First fit average block size: %ld\n", test_heap(HEAP_FIRSTFIT, 50000));
	printf("Next fit average block size: %ld\n", test_heap(HEAP_NEXTFIT, 50000));
	printf("Best fit average block size: %ld\n", test_heap(HEAP_BESTFIT, 50000));

}
