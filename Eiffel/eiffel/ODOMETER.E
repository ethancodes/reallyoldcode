class ODOMETER creation
make

feature{any}
        slots:  INTEGER--number of digits the odometer has
        odom:   ARRAY[MODCNTER]--main array...the odometer itself
        io:     BASIC_IO
        base:   INTEGER--top limit of the odometer, usually 10
        temp:   MODCNTER--a temporary variable for stuffing into the array

feature{any}
make(n:INTEGER) is
        --creation procedure
local
        i:      INTEGER
do
        base:=  10
        !!odom.make(1,n)
        slots:= n
        from
                i:=     1
        until
                i=      slots+1
        loop
                !!temp.make
                odom.put(temp,i)
                i:=     i+1
        end--loop
end--make
                     
display is
        --displays the current value
local
        i:      INTEGER
do
        !!io
        io.put_newline
        io.put_string("Value = ")

        from
                i:=     1
        until
                i=      slots+1
        loop
                io.put_int(odom.item(i).count)
                i:=     i+1
        end--loop

        io.put_newline

end--display

increment is
        --increments the odometer
local
        i:      INTEGER
do
        odom.item(slots).increment
        if odom.item(slots).overflow=TRUE then
                from
                        i:=     slots
                until
                        i=      0
                loop
                        if odom.item(i).overflow=TRUE then
                                !!temp.make
                                odom.put(temp,i)
                                if i>1 then
                                        odom.item(i-1).increment
                                end--if
                        end--if

                        i:=     i-1

                end--loop
        end--big if
end--increment procedure

end--class ODOMETER
