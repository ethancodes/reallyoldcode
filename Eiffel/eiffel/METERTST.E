class METERTST creation
make

feature
        m1,m2:  ODOMETER
        i:      INTEGER

make is
        --main procedure
do
        !!m1.make(3)
        !!m2.make(3)
        m1.display
        m2.increment
        m2.display

        from
                i:=     0
        until
                i=      234
        loop
                m1.increment
                i:=     i+1
        end--loop

        m1.display

        from
                i:=     0
        until
                i=      2139
        loop
                m2.increment
                i:=     i+1
        end--loop

        m2.display
end--make

end--metertst
