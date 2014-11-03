import random


def create_int_list(size, min_val, max_val):
    """ Creates an list of the given size filled with random number in
    interval [min,max].

    Args:
        size (int): The size of the array
        min_val(int): Minimal(included) possible value
            of the elements of the list
        max_val(int): Maximal(included) possible value
            of the elements of the list

    Returns:
        created list with random integers

    """
    array = []
    i = 0
    while i < size:
        ran = random.randint(min_val, max_val)
        array.append(ran)
        i = i + 1
    return array


def swap(lst, i, j):
    """ Swaps element i and j in a list. """
    lst[i], lst[j] = lst[j], lst[i]


def bubble_sort(array):

    while True:
        swapped = False

        for i in range(len(array) - 1):
            if array[i] > array[i + 1]:
                # Swap them
                swap(array, i, i + 1)
                swapped = True

        print array
        if not swapped:  # Loop while there was at least one swap
            break


def insertion_sort(array):

    for i in range(1, len(array)):
        # Repeat until start of the array is hit or while there is still
        # a bigger element before the current one
        k = i
        # Save value
        val = array[i]
        while k > 0 and array[k - 1] > val:
            array[k] = array[k - 1]  # Move the element on previous position to current position
            k = k - 1
        array[k] = val  # If nothing was moved we are just assigning val to index of val => no change

        print array


def selection_sort(array):

    # No need to sort the last sub-array of one element
    for i in range(0, len(array) - 1):
        # For each position in array find the lowest value in the unsorted part
        # of array
        min_index = i
        for j in range(i, len(array)):
            if array[j] < array[min_index]:
                min_index = j

        if (min_index != i):  # If minimum value other than on index i was found
            swap(array, i, min_index)


def gapped_insert_sort(array, gap):

    for i in range(gap, len(array)):
        j = i
        val = array[j];
        while j >= gap and array[j - gap] > val:
            array[j] = array[j - gap]
            j -= gap
        array[j] = val
        print "   --------", array
        i += 1

        
def shell_sort(array):
    gap = len(array) / 2;

    while gap > 0:
        print "Gap:", gap
        gapped_insert_sort(array, gap)
        gap /= 2
        print "--------", array

def merge_sort(array, start, end):
    # Excluding end
    
    if end < start:
        raise ValueError("End " + str(end) + " is smaller than start " + str(start))
    llen = end - start
    
    
    if llen == 1:
        return
    
    split = start + llen / 2;
    
    merge_sort(array, start, split)
    merge_sort(array, split, end)
    
    first_half_cpy = array[start:split]
    
    k = start
    i = 0
    j = split
    
    while i < (split - start) and j < end:
        if (array[j] < first_half_cpy[i]):
            array[k] = array[j]
            j += 1
        else:
            array[k] = first_half_cpy[i]
            i += 1
        k += 1
    
    while i < split - start:  # Copy all the left elements in the first half if there are any left
        array[k] = first_half_cpy[i]
        i += 1
        k += 1
    # If there are any elements left in the  second half they are already in place then
    
    print " ------ ", array
        

def heap_sort(array):
    pass
    
def heapify(array):
    for i in range(0, len(array)):
        sift_up(array, i)
        
def sift_up(array, index):
    if index == 0:
        return
    parent = (index - 1) / 2
    if (array[parent] >= array[index]):
        #Everything in ordnung
        return
    #swap
    array[parent], array[index] = array[index], array[parent]
    #..and repeat the process
    sift_up(array, parent)
    
    

    

def main():
    size = 30
    min_val = 0
    max_val = 10
    array = create_int_list(size, min_val, max_val)
    print "Initial:", array
    # bubble_sort(array)
#     insertion_sort(array)
#     selection_sort(array)
#     merge_sort(array, 0, len(array))
#     heap_sort(array)
    
    heapify(array)

    print " Sorted:", array

main()
