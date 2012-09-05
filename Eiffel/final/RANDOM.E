class RANDOM

feature
        mathman: expanded MATH

        randomize(x: INTEGER) is
        -- randomize
        do
                mathman.srand(x)
        end -- randomize

        number(max :INTEGER): INTEGER is
        -- return a random number between 1 and max
        local
                x: INTEGER
        do
                -- randomize(max)
                from x := max+1
                until (x <= max) and (x >= 1)
                loop
                        x := mathman.rand \\ 10
                end
                result := x
        end -- number
end -- class RANDOM
                
