class BUG
        creation make
feature
        xpos: INTEGER
        ypos: INTEGER

        make(x, y: INTEGER) is
        -- create a bug
        do
                xpos := x
                ypos := y
        end -- make

        setxy(x, y: INTEGER) is
        -- set the x and y positions
        do
                xpos := x
                ypos := y
        end -- setxy

        showpos is
        -- show the position of the bug in coordinates
        local
                io: expanded BASIC_IO
        do
                io.put_newline
                io.put_string("BUG @ ")
                io.put_int(xpos)
                io.put_string(" ")
                io.put_int(ypos)
        end -- showpos
                
end -- class BUG
                
     
