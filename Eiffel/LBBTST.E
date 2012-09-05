class LBBTST creation
        -- test for LBB
        -- Ethan Georgi
        make
feature
        io: expanded BASIC_IO
        thebook: LBB

        make is
        local
                done: BOOLEAN
                user: STRING
        do
                !!thebook.make
                from done := false
                until done
                loop
                        io.put_string("Enter - A - to add a person")
                        io.put_newline
                        io.put_string("Enter - R - to retrieve a person")
                        io.put_newline
                        io.put_string("Enter - Q - to quit")
                        io.put_newline
                        io.put_string("> ")
                        io.get_string
                        user := io.last_string
                        if (user.is_equal("A")) or (user.is_equal("a")) then
                           addnew
                        end
                        if (user.is_equal("R")) or (user.is_equal("r")) then
                           retrieve
                        end
                        if (user.is_equal("Q")) or (user.is_equal("q")) then
                           done := TRUE
                        end
                end -- loop
                thebook.save
        end -- make

        addnew is
        -- testing if it can really add a new person to the file
        local
                name, phone: STRING
        do
                io.put_newline
                io.put_string("Enter the new person's name: ")
                io.get_string
                name := io.last_string
                io.put_newline
                io.put_string("Enter the new person's phone number: ")
                io.get_string
                phone := io.last_string
                thebook.add(name, phone)
        end -- addnew

        retrieve is
        -- testing if it can give us the info
        local
                name: STRING
        do
                io.put_newline
                io.put_string("Enter the person's name: ")
                io.get_string
                name := io.last_string
                io.put_newline
                thebook.get(name)
        end -- retrieve
end -- class LBBTST



