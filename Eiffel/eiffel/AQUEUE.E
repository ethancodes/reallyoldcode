     class AQUEUE[ITEM] creation
     make
     feature{NONE}
     store:ARRAY[ITEM]
     size:INTEGER
     head:INTEGER
     tail:INTEGER
     feature{ANY}
     make(n:INTEGER) is --initializes the AQUEUE
     do
       size:=n
       !!store.make(1,n)
       tail:=1
       head:=1
       length:=0
       end--make
   front:ITEM is--the item at the front of the AQUEUE
                --can't be empty
   do
     RESULT:=store.item(head)
   end--front
   add(i:ITEM) is--adds i to the end, must not be full
   do
     store.put(i,tail)
     tail:=1+tail \\ size
     length:=length+1
   end--add
   remove is--removes 1st item IF it's not empty
   do
     head:=1+head\\size
     length:=length-1
   end--remove
   is_empty:BOOLEAN--returns true if AQUEUE is empty
   do
     result:=(length=0)
   end--is_empty
   is_full:BOOLEAN--returns true if AQUEUE is full
   do
     result:=(length=size)
   end--is_full

   length:INTEGER

   end--class AQUEUE
