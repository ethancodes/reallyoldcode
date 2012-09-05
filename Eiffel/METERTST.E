class METERTST creation
        -- tests class MODCNTER
        make
feature
        make is
                -- initialize the sucker
        local
                m1, m2: MODCNTER
                io: BASIC_IO
        do
                !!io
                !!m1.make1
                !!m2.make2(5)
                -- start all the tests
                io.put_string("m1: ")
                m1.display
                io.put_string("m2: ")
                m2.display
                -- show that both are empty
                m1.increment
                io.put_string("m1: ")
                m1.display
                m2.increment
                io.put_string("m2: ")
                m2.display
                -- show that each can increment once to 1
                m1.decrement
                m1.decrement
                io.put_string("m1: ")
                m1.display
                -- show that m1 can DECREMENT WRAP
                m2.increment
                m2.increment
                m2.increment
                m2.increment
                m2.increment
                io.put_string("m2: ")
                m2.display
                -- show that m2 can INCREMENT WRAP
        end -- make
end -- class metertst.e
        



