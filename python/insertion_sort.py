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
        i = i+1
    return array


def swap(lst, i, j):
    """ Swaps element i and j in a list. """
    lst[i], lst[j] = lst[j], lst[i]


def bubble_sort(array):

    while True:
        swapped = False

        for i in range(len(array) - 1):
            if array[i] > array[i+1]:
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
        while k > 0 and array[k - 1] > array[k]:
            swap(array, k, k - 1)
            k = k - 1

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

        print array


def main():
    size = 20
    min_val = 0
    max_val = 10
    array = create_int_list(size, min_val, max_val)
    print array
    # bubble_sort(array)
    # insertion_sort(array)
    selection_sort(array)
    print array

main()
