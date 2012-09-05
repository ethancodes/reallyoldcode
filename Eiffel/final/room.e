class ROOM
        creation make
feature
        length: INTEGER
        width: INTEGER
        floor: ARRAY[tile]

        make(l, w: INTEGER) is
        -- create the floor of the room
        local
                x, y: INTEGER
                t: TILE
        do
                length := l
                width := w
                !!floor.make(1,(length*width)+1)
                from x := 1
                until x = (length*width)+1
                loop
                        !!t.make
                        floor.put(t,x)
                        x := x + 1
                end -- loop
        end -- make

        showroom is
        -- show the room
        local
                x, y: INTEGER
                io: expanded BASIC_IO
                tile: INTEGER
        do
                from x := 1
                until x = length+1
                loop
                        io.put_newline
                        from y := 1
                        until y = width+1
                        loop
                                tile := ((x-1)*width)+y
                                io.put_int(floor.item(tile).hits)
                                io.put_string(" ")
                                y := y + 1
                        end
                        x := x + 1
                end -- loop
                io.put_newline
        end -- showroom
                        
end -- class ROOM
