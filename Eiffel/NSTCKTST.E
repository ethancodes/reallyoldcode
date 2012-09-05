class NSTCKTST
        creation make
feature
        make is
                -- test the NEWSTACK
	local
                s: NEWSTACK
                io: expanded BASIC_IO
                c: INTEGER
	do
                !!s.make
                from c := 1
                until c = 11
                loop
                        s.push(c)
                        c := c + 1
                end
                s.setfp(4)
                io.put_string("setting FP to 4")
                io.put_newline
                io.put_string("the item with offset 3: ")
                io.put_int(s.getitem(3))
                io.put_newline
                io.put_string("changing item with offset 3")
                s.putitem(0,3)
                io.put_newline
                io.put_string("allocating 3")
                io.put_newline
                s.allocate(3)
                s.display
                io.put_newline
                io.put_string("deallocating 4")
                io.put_newline
                s.deallocate(4)
                s.display
        end -- make
end -- class nstcktst

