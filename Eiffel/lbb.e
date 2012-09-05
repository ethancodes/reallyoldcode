class LBB creation
        -- the Little Black Book
        -- Ethan Georgi
        make
feature
        fsystem: FILE_SYSTEM
        book: FILE[PERSON]
        hook: INTEGER   -- captain?
        size: INTEGER   -- the number of people in the book
        io: expanded BASIC_IO
        newperson: PERSON       -- newbies
        
        make is
        -- create, or retrieve, the LBBOOK
        do
                !!fsystem.make
                if not fsystem.file_exists("LBBOOK") then
                   fsystem.add_file("LBBOOK","rw")
                end -- gee i hate if then end's
                   -- if the file doesn't exist, create it

                hook := fsystem.access_file("LBBOOK", "rw", false)
                -- false because we don't want to overwrite the data

                !!book.connect_to(hook)
                size := book.count
        end -- make
                
        add(n: STRING; p: STRING) is
        -- add a person into the file
        do
                size := size + 1
                !!newperson.make(n, p)
                book.put(newperson, size)
                -- puts the new person in a new space
        end -- add

        get(p: STRING) is
        -- search the file for the info on the person entered
        local
                x: INTEGER -- a counter
        do
                from x := 1
                until (x > size) or (book.item(x).name.is_equal(p))
                loop
                        x := x + 1
                end
                book.item(x).print
                io.put_newline
        end -- get

        save is
        -- when they choose to quit it must save all their work
        do
                book.disconnect
        end -- save
end -- class LBB
                
                             
