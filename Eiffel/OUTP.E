class OUTP creation
        make
feature
        make is
        -- create a person and output details on the screen
        local
                p: PERSON
        do
                !!p.make("S. Brown", "23 High St.", 1960)
                p.print
        end --make
end --class outp
