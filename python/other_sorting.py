# from sorting import create_int_list
import random

from sorting import create_int_list


def merge_sort(array, start, end):
    """ start index (including), end index (including)
    """
    pivot = partition(array, start, end) #pivot index
    if pivot - start > 1:
        merge_sort(array, start, pivot - 1)
    if end - pivot > 1:
        merge_sort(array, pivot + 1, end)

def partition(array, start, end):
    
    assert start < end
    
    #Get pivot index
    pivot_index = (end + start) / 2 #Just in the middle
    pivot = array[pivot_index]

    #Replace pivot with 1st
    array[start], array[pivot_index] = array[pivot_index], array[start]
    
    left = start + 1
    right = end
    while True:
        while array[left] <= pivot and left < end:
            left += 1
        while array[right] > pivot:
            right -= 1
        if left >= right:
            #We are done here
            break
        array[left], array[right] = array[right], array[left]
    #We know the pivot is at the start index and 'right' 
    #index is place where the pivot should be
    array[start], array[right] = array[right], array[start]
    
    return right

size = 30
min_val = 0
max_val = 100
array = create_int_list(size, min_val, max_val)


print array
# i = partition(array, 0, len(array) - 1)
# print array, "pivot index", i,"=>", array[i]
merge_sort(array, 0, len(array) - 1)
print array
