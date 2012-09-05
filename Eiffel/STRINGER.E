class STRINGER creation
    make
feature
    io: BASIC_IO
    f: FORMAT
    fini, st, s: STRING
    x: REAL
    y, z: INTEGER
    done: BOOLEAN
    ch: CHARACTER

    make is
    do
        !!io
        !!f
        done := false
        io.put_string("Enter a number, a space, then another number ")
        io.get_string
        st := io.last_string
        io.put_string("STRING: ")
        io.put_string(st)
        io.put_newline
        z := 1
        fini := st.substring(z,z)
        from y := z + 1
        until (y > st.count) or (done)
        loop
                s := st.substring(y,y)
                ch := f.s2c(s)
                if ch = ' ' then done := true
                else fini.append(s)
                end
                y := y + 1
        end
        io.put_string("***")
        io.put_real(f.s2r(fini))
        io.put_string("***")
    end -- make
end -- class stringer

        
