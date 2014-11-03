import math


class Heap:
    """ Implementation of max heap """
    
    def __init__(self):
        self.__list = []
    
    def add(self, val):
        self.__list.append(val)
        self.__upheap(len(self.__list) - 1)

    
    def __upheap(self, index):
        """ Verify if the element at the given index is properly positioned.
           If not, make it flow upwards until it is at the correct position.
        """
        if index == 0: 
            # Is root
            return

        parent = self.__get_parent_index(index)
        if (self.__list[parent] > self.__list[index]):
            # The position is ok
            return
        
        # Parent is smaller than this element
        # Swap
        self.__list[parent], self.__list[index] = self.__list[index], \
            self.__list[parent] 
        
        # Call recursively on the new position (now containing the element not its parent)
        self.__upheap(parent)

    
    def __get_parent_index(self, index):
        return (index - 1) / 2;
    
    def __get_left_child_index(self, index):
        return index * 2 + 1

    def __get_right_child_index(self, index):
        return index * 2 + 2
    
    def __str__(self):
        return "Heap:" + self.__list.__str__()
    
    def get_depth(self):
        size = len(self.__list)
        levels = math.ceil(math.log(size, 2))
        levels = int(levels)
        return levels

    class Row:
        
        space = None
        space_str = "     "

        def __init__(self):
            self.list = []
            self.is_base = False
            self.is_top = False
            self.parent = None
            self.space_siblings = 1

        
        def __str__(self):
            return "Row " + self.list.__str__()
        
        def to_str(self):
            second = ""
            for e in self.list:
                if e == None: 
                    second += Heap.Row.space_str
                else:
                    second += "|" + str(e).zfill(3) + "|"
            
            #Init top row with space strings
            #Top row is the row where /  \ will be shown
            top_row = [Heap.Row.space_str for i in range(0, len(self.list))]
            
            if row.is_top:
                #There will be no top row for the first level
                return second;

            for i in range(0, len(self.list)):
                val = self.list[i]
                if val != None:
                    if val % 2 == 1:
                        # Left child
                        top_row[i + 1] = "/ "
                    else:
                        # Right right - val % 2 == 0
                        top_row[i - 1] = " \\"
            
            first = ""
            for s in top_row:
                first += s

#             return first + "\n" + second
            return second
                
     
    def build_row(self, level):
        depth = self.get_depth()
        if (level >= depth):
            raise ValueError("The level out of range. Max can be " + depth)
        

        row = Heap.Row()
        first = 2 ** level - 1  # List index of the first leaf in the base level

        if (level == depth - 1):
            # Base level
            row.is_base = True
            row.space_siblings = 1
            space_pairs = 3

            leaf_count = 2 ** level
            last = first + leaf_count - 1
            for i in range(first, last + 1):
                
                row.list.append(i)  # TODO change to value from the list

                if i % 2 == 1: 
                    # First sibling
                    spaces = [Heap.Row.space for j in range(0, row.space_siblings)]  # @UnusedVariable
                    row.list.extend(spaces)

                if i != last and i % 2 == 0:
                    # Second sibling (but not the last leaf in the row)
                    spaces = [Heap.Row.space for j in range(0, space_pairs)]  # @UnusedVariable 
                    row.list.extend(spaces)
            return row

        # Non-base level
        assert level < depth - 1
        
        # Parent row is row below this row
        parent_row = self.build_row(level + 1)
        row.parent = parent_row
        
        row.list = [Heap.Row.space for i in range(0, len(parent_row.list))]  # Init to given length with None
        offset = (parent_row.space_siblings + 2 - 1) / 2  # Offset to the left from the first children below
        index = first
        for i in range(0, len(parent_row.list)):
            val = parent_row.list[i]
            if val != Heap.Row.space and val % 2 == 1:
                # If this is the first children of the node
                row.list[i + offset] = index
                index += 1
        
        first_sibling = None
        second_sibling = None
        # Calculate spacing between siblings
        for i in range(0, len(row.list)):
            val = row.list[i]
            
            if (val != Heap.Row.space):
                if first_sibling == None:  # First node found
                    first_sibling = i
                elif second_sibling == None:  # Second node found
                    second_sibling = i
                else:
                    break
        
   
        if second_sibling != None:  # In case of the very first row there will be no second sibling
            row.space_siblings = second_sibling - first_sibling - 1
        else:
            row.is_top = True
        
        return row

        
        
        
        
        
        


            



h = Heap()
 
h.add(4)
h.add(5)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(4)
h.add(5)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(4)
h.add(5)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(4)
h.add(5)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(4)
h.add(5)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(3)
h.add(20)

h.add(20)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(4)
h.add(5)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(0)
h.add(7)
h.add(3)
h.add(20)
h.add(4)
h.add(5)
h.add(9)
h.add(7)
h.add(0)
h.add(7)
h.add(3)
h.add(20)


row = h.build_row(0)
while True:
    print row.to_str()
    if row.is_base:
        break
    row = row.parent







