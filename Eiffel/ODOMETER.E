class ODOMETER creation
        make
feature
        meter: ARRAY[modcnter]
                -- this is the odometer... WHOO-HOO!
        size: INTEGER
                -- to keep the size of the odometer
        make(n: INTEGER) is
                -- creation procedure
                !!meter.make(1,n)
                size := n
        end -- make
        increment is
                -- increments the odometer
                meter.modcnter(size).increment
                if meter.modcnter(size) = 9 then
                        inc(size-1)
                end
        end -- increment
        inc (x: INTEGER) is
                -- my adventures in recursiveness
                meter.modcnter(x).increment
                if meter.modcnter(x) = 9 then
                        if x > 1 then
                                inc(x-1)
                        end
                end
        end -- inc
end -- class odometer
