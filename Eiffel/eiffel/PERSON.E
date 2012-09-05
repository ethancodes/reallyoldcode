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


