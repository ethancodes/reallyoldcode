class AQUEUE[ITEM] creation
        make
feature {NONE}
        store: ARRAY[ITEM]      -- an array of elements of type ITEM
        size: INTEGER           -- the maximum size of queue
        head: INTEGER           -- index of first element in queue
        tail: INTEGER           -- index of the first available space in array
feature {ANY}
        make(n: INTEGER) is
                -- intializes the queue
                do
                        size := n          -- save the value of n
                        !!store.make(1.n)  -- create a new array of size n
                        tail := 1          -- initialize index of tial of queue
                        head := 1          -- initialize start position of queue
                        length := 0        -- initial length of queue
        end --make
        front: ITEM is
                -- the item at the front of the queue
                -- pre-condition: not is_empty
                do
                        result := store.item(head) -- return the item at
                                                   -- position head of store
        end --front
        add(i: ITEM is
                -- adds the item i to the end of the queue
                -- pre-condition: not is_full
                do
                        store.put(i, tail)      -- place item i at position tail
                        tail := 1+tail \\ size  -- \\ yields the remainder (mod?)
                                                -- after integer division
                        length := length + 1
        end --add
        remove is
                -- removes the item at the front of the queue
                -- pre-condition: not is_empty
                do
                        head := 1 + head \\ size
                        length := length - 1
        end --remove
        is_empty: BOOLEAN
                -- returns true if the queue is empty and false otherwise
                do
                        result := (length = 0)
        end --is_empty
        is_full: BOOLEAN
                -- returns true if the queue is full and false otherwise
                do
                        result := (length = size)
        end --is_full
        length: INTEGER                 -- current length of queue
end --class AQUEUE

