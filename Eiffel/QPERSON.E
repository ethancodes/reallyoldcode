class QPERSON creation
        make
feature
        make is
                local
                        p1, p2: PERSON
                        q: AQUEUE[PERSON]
                do
                        !!q.make(10)
                        !!p1.make("S. Brown", "23 High St.", 1960)
                        !!p2.make("P. Jones", "551 Tower Block", 1961)
                        q.add(p1)
                        q.add(p2)
                        q.front.print
                        q.remove
                        q.front.print
        end --make
end --class QPERSON

