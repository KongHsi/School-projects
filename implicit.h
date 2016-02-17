/*
* Search algorithm used for the heap.
*/
#define HEAP_FIRSTFIT 0
#define HEAP_NEXTFIT  1
#define HEAP_BESTFIT  2

/*
* Maximum amount of empty space in a block.
*/
#define MAX_UNUSED_BYTES 128

/*
* Struct used to represent the heap.
*/
typedef struct
{
	int search_alg; /* Search algorithm. */
	long size;      /* Size of the heap in bytes. */
	char *next;     /* Next block to try (for next fit only). */
	char *start;    /* Array of "size" characters for the heap. */
} heap;

/*
* Create a heap that is "size" bytes large.
*/
heap *heap_create(long size, int search_alg);

/*
* Dispose (free) of the heap.
*/
void heap_dispose(heap *h);

/*
* Print the structure of the heap to the screen.
*/
void heap_print(heap *h);

/*
* Determine the average size of a free block.
*/
long heap_find_avg_free_block_size(heap *h);

/*
* Free a block on the heap h.
*/
void heap_free(heap *h, char *payload);

/*
* Our implementation of malloc.
*/
void *heap_malloc(heap *h, long size);

