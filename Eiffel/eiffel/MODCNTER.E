--Jeff Novakouski
--Eiffel
--9/25
--MODCNTER assignment

class MODCNTER creation
        make,
        --default, creates a counter base 10
        make2
        --creates a counter base modu

feature
        base:           integer--the modulo
        value:          integer--the value stored in the counter
        io:             BASIC_IO
        overflow:       BOOLEAN--checks if a variable rolls over
        underflow:      BOOLEAN--checks if a variable rolls under


make is
        --default, creates a counter of base 10
do
        base:=  10
        value:= 0
end--make

make2(n:integer) is
        --creates a counter of base modu
do
        base:=  n
        value:= 0
end--make2

increment is
        --increments the counter
do
        value:= value+1
        if value=base then
                overflow:=      true
        end--if
end--increment

decrement is
        --decrements the counter
do
        value:= value-1
        if value=-1 then
                underflow:=     TRUE
        end--if
end--decrement

count:INTEGER is
        --returns the counter value
do
        Result:=        value
end--count

display is
        --displays the counter value and base(modulo)
do
        !!io
        io.put_string("The value is ")
        io.put_int(value)
        io.put_newline
        io.put_string("The modulo is ")
        io.put_int(base)
end--display

end--MODCNTER
