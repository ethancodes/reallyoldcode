class POSTFIXR creation
    -- evaluates postfix expressions
    -- written by Ethan Georgi
    make
feature
    fix: ASTACK[REAL]   -- where i plan to store the values
    io: BASIC_IO        -- so we can input and output
    f: FORMAT           -- the cursed class FORMAT
    pe: STRING          -- the entered expression

    make is
        -- get the expression and evaluate it
    local
        ch, dumch: CHARACTER   -- character
        dum, dum2: STRING     -- dummy string
        x, y: INTEGER   -- counters
        lngth: INTEGER  -- the number of characters in the string
        done: BOOLEAN   -- done searching for spaces?
    do
        !!fix.create    -- make the stack
        !!io
        !!f
        y := 1
        io.put_string("Please enter your Postfix expression: ")
        io.get_string
        pe := io.last_string
        lngth := pe.count
        from x := 1
        until x > lngth
        loop
            done := false
            dum2 := pe.substring(x,x)
            ch := f.s2c(dum2)
            inspect ch
                when ' ' then y := y + 1
                when '+' then y := y + 1
                              Add
                when '-' then y := y + 1
                              Sub
                when '/' then y := y + 1
                              Dvd
                when '*' then y := y + 1
                              Mlt
                else -- the character must be a number!
                        from y := x + 1
                        until (y > lngth) or (done)
                        loop
                                dum := pe.substring(y,y)
                                dumch := f.s2c(dum)
                                if dumch = ' ' then done := true
                                else dum2.append(dum)
                                end
                                y := y + 1
                        end -- loop
                        fix.push(f.s2r(dum2))
            end -- inspect
            x := y
        end -- loop
        io.put_string("The answer is ")
        io.put_real(fix.top)
    end -- make

    add is
        -- add the top two values in the stack
    local
        num1, num2: REAL
    do
        num1 := fix.top
        fix.pop
        num2 := fix.top
        if fix.length > 1 then fix.pop
        end
        fix.push(num2+num1)
    end -- add

    sub is
        -- subtract the top two values in the stack
    local
        num1, num2: REAL
    do
        num1 := fix.top
        fix.pop
        num2 := fix.top
        if fix.length > 1 then fix.pop
        end
        fix.push(num2-num1)
    end -- sub

    dvd is
        -- divide the top two values in the stack
    local
        num1, num2: REAL
    do
        num1 := fix.top
        fix.pop
        num2 := fix.top
        if fix.length > 1 then fix.pop
        end
        fix.push(num2/num1)
    end -- dvd

    mlt is
        -- multiply the top two values in the stack
    local
        num1, num2: REAL
    do
        num1 := fix.top
        fix.pop
        num2 := fix.top
        if fix.length > 1 then fix.pop
        end
        fix.push(num2*num1)
    end -- mlt
end -- class PostFixr





