from queue import PriorityQueue
from re import T

'''READ ME
This file seeks to look at sorting algorithms in order to look at bigO complexity.'''
toSort = [5,4,3,2,1,4,5,6,99,9,10,23,69,38,495,95]



def bubbleSort(lst):
    '''O(n^2)'''
    n = len(lst)
    for i in range(n-1):
        for j in range(0, n-i-1):
            if lst[j] > lst[j+1]:
                lst[j],lst[j+1] = lst[j+1],lst[j]
    return lst

print(f'The array ordered by bubbleSort is {bubbleSort(toSort)}')
print(f'is the array sorted correctly? {sorted(toSort) == bubbleSort(toSort)}\n\n\n')

def priorityQueueSort(lst):
    '''O(n^2), will require extra space compared to in-place sorting.'''
    pq = PriorityQueue()
    for i in range(len(lst)):
        pq.put(lst[i], None)
    for i in range(len(lst)):
        lst[i] = pq.get()
    return lst

print(f'The array ordered by PqSort is {priorityQueueSort(toSort)}')
print(f'is the array sorted correctly? {sorted(toSort) == priorityQueueSort(toSort)}\n\n\n')

def selectionSort(lst):
    '''O(n^2)'''
    n = len(lst)
    i = 0
    while i < n:
        smallest = i
        j = i+1
        while j < n:
            if lst[j] < lst[smallest]:
                smallest = j
            j += 1
        lst[i], lst[smallest] = lst[smallest], lst[i]
        i += 1
    return lst

print(f'THe array ordered by selectionSort is {selectionSort(toSort)}')
print(f'is the array sorted correctly? {sorted(toSort) == selectionSort(toSort)}\n\n\n')

def insertionSort(lst):
    '''O(n^2) Omega(n)'''
    n = len(lst)
    i = 1
    while i < n:
        j = i-1
        while lst[i] < lst[j] and j > -1:
            j -= 1
        temp = lst[i]
        k = i-1
        while k > j:
            lst[k+1] = lst[k]
            k -= 1
        lst[k+1] = temp
        i += 1
    return lst

print(f'The array ordered by insertionSort is {insertionSort(toSort)}')
print(f'is the array sorted correctly? {sorted(toSort) == insertionSort(toSort)}\n\n\n')


def quickSort(start, end, lst):
    '''O(n^2), Omega(nlogn), SpaceComplexity:logn'''
    def merger(start, end, lst):
        pivot_index = start
        pivot = lst[pivot_index]

        while start < end:
            while (start < len(lst)) and (lst[start] <= pivot):
                start += 1

            while lst[end] > pivot:
                end -=1

            if start < end:
                lst[start], lst[end] = lst[end], lst[start]

        lst[end], lst[pivot_index] = lst[pivot_index], lst[end]
        return end
    

    if start < end:
        mergedValues = merger(start, end, lst)
        quickSort(start, mergedValues - 1, lst)
        quickSort(mergedValues+1, end, lst)
    
    return lst

print(f'The array ordered by quickSort is {quickSort(0, len(toSort)-1, toSort)}')
print(f'is the array sorted correctly? {sorted(toSort) == quickSort(0, len(toSort)-1, toSort)}\n\n\n')

def heapSort(lst):
    '''O(nlogn)'''
    def heapify(lst, n, i):
        largest = i
        left, right = 2 * i + 1, 2 * i + 2

        if (right < n) and lst[largest] < lst[right]:
            largest = right

        if (left<n) and lst[largest] < lst[left]:
            largest = left

        if largest is not i:
            lst[i], lst[largest] = lst[largest], lst[i]

            heapify(lst, n, largest)

    n = len(lst)

    for i in range(n//2 - 1, -1, -1):
        heapify(lst, n, i)
    
    for i in range(n-1, 0, -1):
        lst[i], lst[0] = lst[0], lst[i]
        heapify(lst, i , 0)
    
    return lst

print(f'The array ordered by heapSort is {heapSort(toSort)}')
print(f'is the array sorted correctly? {sorted(toSort) == heapSort(toSort)}\n\n\n')


def insertion_sort(array):
     # Loop from the second element of the array until
     # the last element
     for i in range(1, len(array)):
         # This is the element we want to position in its
         # correct place
            key_item = array[i]
 
         # Initialize the variable that will be used to
        # find the correct position of the element referenced
        # by `key_item`
            j = i - 1

        # Run through the list of items (the left
        # portion of the array) and find the correct position
        # of the element referenced by `key_item`. Do this only
        # if `key_item` is smaller than its adjacent values.
            while j >= 0 and array[j] > key_item:
             # Shift the value one position to the left
             # and reposition j to point to the next element
             # (from right to left)
                array[j + 1] = array[j]
                j -= 1

        # When you finish shifting the elements, you can position6        # `key_item` in its correct location
            array[j + 1] = key_item

     return array


print(insertion_sort([5,3,4,2]))
