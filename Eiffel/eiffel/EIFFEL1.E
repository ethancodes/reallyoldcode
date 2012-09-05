 class PERSON creation
 make
 feature
 make(n:STRING; a:STRING; y:INTEGER) is
 --Initializes a person
 do
 name:=n
 address:=a
 year_of_birth:=y
 end--make
 name:STRING
 address:STRING
 year_of_birth:INTEGER

 feature
 print is
 --print person's details n screen
 local
      io:BASIC_IO
 do
 !!io
 io.put_string("Name:          ")
 io.put_string(name)
 io.put_newline
 io.put_string("Address:       ")
 io.put_string(address)
 io.put_newline
 io.put_string("Year of Birth: ")
 io.put_int(year_of_birth)
 io.put_newline
 end--print
 end--class PERSON


class OUTP creation
make
feature
make is
--create a person and output details on the screen
local
    p:PERSON
do
!!p.make("S. Brown", "23 High St", 1960)
p.print
end--make

end--class OUTP

class QPERSON creation
    make
feature
    make is
    --create a QPERSON
    local
         per1,per2:PERSON
         q:AQUEUE[PERSON]
    do
      !!q.make(10)
      !!per1.make("Jimbo","Noplace",1999)
      !!per2.make("Bob the Plumber","24 Hiccup Street",1941)
      q.add(per1)
      q.add(per2)
      q.front.print
      q.remove
      q.front.print
    end--make
     end--class QPERSON

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
