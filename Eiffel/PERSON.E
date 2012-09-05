class PERSON creation
        make

feature
        name: STRING
        phone: STRING

        make(n: STRING; p: STRING) is
                -- initializes a person
                do
                        name := n
                        phone := p
        end -- make

        print is
                -- Print person's details on screen
                local
                        io:basic_io
                do
                        !!io
                        io.put_string("Name: ")
                        io.put_string(name)     -- prints the name of person
                        io.put_newline          -- moves to a new line
                        io.put_string("Phone: ")
                        io.put_string(phone)
                        io.put_newline
        end -- print
end --class PERSON
