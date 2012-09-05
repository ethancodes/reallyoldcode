class MODCNTER creation
        make1, make2
feature
        modulo: INTEGER
        max: INTEGER
        make1 is
                -- create a modulo counter with modulo 10
        do
                modulo := 0
                max := 10
        end -- make1
        make2(modu: INTEGER) is
                -- create a modulo counter with modulo modu
                -- assume modu > 0
        do
                modulo := 0
                max := modu
        end -- make2
        increment is
                -- increment the modulo counter
        do
                if modulo <= (max-2) then
                        modulo := modulo + 1
                else
                        modulo := 0
                end -- if modulo
        end -- increment
        decrement is
                -- decrement the modulo counter
        do
                if modulo > 0 then
                        modulo := modulo - 1
                else 
                        modulo := max-1
                end -- if modulo
        end -- decrement
        count: INTEGER is
                -- returns the modulo counter's value
        do
                result := modulo
        end -- count
        display is
                -- display the counter value and modulo
        local
                io: BASIC_IO
        do
                !!io
                io.put_newline
                io.put_string("THE COUNTER IS ")
                io.put_int(count)
                io.put_newline
        end -- display
end -- class MODCNTER


              
