class NEWSTACK
        inherit ASTACK  -- NEWSTACK "is_a" ASTACK
        creation MAKE
feature {NONE}
        fp: ANODE[integer]

feature {ANY}

        make is
        -- create the newstack
        local
                i: ANODE[integer]
        do
                create
                -- run the original creation procedure
                fp := front
        end -- make
        
        setfp(x: INTEGER) is
        -- set the frame pointer to x, which is less than top
        local
                c: INTEGER
        do
                fp := front
                from c := 1
                until c = (x-1)
                loop
                        fp := fp.next
                        c := c + 1
                end
        end -- setfp

        getfp: INTEGER is
        -- return the value of the frame pointer
        local
                c: INTEGER
                i: ANODE[integer]
        do
                from c := 1
                     i := front
                until i = fp.next
                loop
                        i := i.next
                        c := c + 1
                end
                result := c
        end -- getfp

        putitem(value: INTEGER; offset: INTEGER) is
        -- will put a new value into the (fp-offset) location
        local
                c: INTEGER
                i,j: ANODE[integer]
                new: ANODE[integer]
        do
                if (getfp-(offset+1)) > 0 then
                        from c := 1
                                i := front
                        until c = (getfp-(offset+1))
                        loop
                                i := i.next
                                c := c + 1
                        end
                        j := i.next
                        j := j.next
                        -- since we can't write to j.item we have to
                        -- create a new node and make the stack link
                        -- to this one instead of the former
                        !!new.create(value)
                        i.link(new)
                        new.link(j)
                else
                        i := front
                        i := i.next
                        !!new.create(value)
                        front := new
                        new.link(i)
                end
        end -- putitem

        getitem(offset: INTEGER):INTEGER is
        -- will retrun the value in the (fp-offset) location
        local
                c: INTEGER
                i: ANODE[integer]
        do
                from c := 1
                     i := front
                until c = (getfp-offset)
                loop
                        i := i.next
                        c := c + 1
                end
                result := i.item
        end -- getitem
        

        allocate(num: INTEGER) is
        -- make room for num new elements
        local
                c: INTEGER
        do
                from c := 0
                until c = num
                loop
                        push(0)
                        c := c + 1
                end
        end -- allocate

        deallocate(num: INTEGER) is
        -- remove num nodes
        local
                c: INTEGER
        do
                from c := 0
                until c = num
                loop
                        pop
                        c := c + 1
                end
        end -- deallocate

        display is
        -- display everything
        local
                io: expanded BASIC_IO
                i: ANODE[integer]
        do
                io.put_string("FP ")
                io.put_int(getfp)
                io.put_newline
                from i := front
                until i.next = void
                loop
                        io.put_int(i.item)
                        io.put_string(" ")
                        i := i.next
                end
                io.put_int(i.item)  -- remember the last item
                io.put_string(" ")
                io.put_string(" <Top:")
                io.put_int(top)
        end -- display

end -- class NEWSTACK
        
