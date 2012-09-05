class WALK
        creation setup
feature
        insect: BUG
        floor: ROOM
        io: expanded BASIC_IO
        move_counter: INTEGER
        rand: expanded RANDOM

        done: BOOLEAN is
        -- done is true when every tile has been hit at least once
        local
                x: INTEGER
        do
                from x := 2
                until ((x > (floor.length*floor.width)) or (floor.floor.item(x-1).hits = 0))
                loop
                        x := x + 1
                end
                if x < floor.length*floor.width then
                        result := false
                elseif floor.floor.item(x-1).hits = 0 then
                        result := false
                else
                        result := true
                end
        end -- done

        validmoves: INTEGER is
        -- compute the number of valid moves
        local
                x, moves: INTEGER
        do
                moves := 8
                from x := 1
                until x = 9
                loop
                        if not valid(x) then
                                moves := moves - 1
                        end
                        x := x + 1
                end
                result := moves
        end -- validmoves

        valid(move: INTEGER): BOOLEAN is
        -- is moving to this location valid?
        do
                inspect move
                when 1 then result := ((insect.xpos > 1) and (insect.ypos > 1))
                when 2 then result := (insect.xpos > 1)
                when 3 then result := ((insect.xpos > 1) and (insect.ypos < floor.width))
                when 4 then result := (insect.ypos > 1)
                when 5 then result := (insect.ypos < floor.width)
                when 6 then result := ((insect.xpos < floor.length) and (insect.ypos > 1))
                when 7 then result := (insect.xpos < floor.length)
                when 8 then result := ((insect.xpos < floor.length) and (insect.ypos < floor.width))
                end
        end -- valid

        setup is
        -- setup the random walk simulation
        local
                l: STRING
                length: INTEGER
                w: STRING
                width: INTEGER
                f: expanded FORMAT
        do
                move_counter := 0
                rand.randomize(5) -- 5 for no reason
                io.put_string("How big is the floor going to be?")
                io.put_newline
                io.put_string("How many squares in length will the floor be? ")
                io.get_string
                l := io.last_string
                io.put_string("How many squares in width will the floor be? ")
                io.get_string
                w := io.last_string
                length := f.s2i(l)
                width := f.s2i(w)
                if (length > 0) and (width > 0) then
                        !!floor.make(length, width)
                        if (length > 1) and (width > 1) then
                           !!insect.make(length/2, width/2)
                        elseif length > 1 then
                           !!insect.make(length/2, 1)
                        else
                           !!insect.make(1, width/2)
                        end                           
                        io.put_string("Start : ")
                        insect.showpos
                        io.put_newline
                        -- floor.showroom
                        io.put_string("Walking")
                        walk
                else
                        io.put_string("Illegal dimensions.")
                end -- if then
        end -- stroll

        walk is
        -- run the random walk simulation
        local
                x, y: INTEGER
                tile: INTEGER 
        do
                from
                until done
                loop
                        hit
                        x := rand.number(validmoves)
                        -- choose one of the valid moves at random
                        from y := 1
                        until x = 0
                        loop
                                -- io.put_int(y)
                                -- io.put_bool(valid(y))
                                if valid(y) then
                                        x := x - 1
                                end
                                y := y + 1
                        end
                        y := y - 1  -- because we're one too high
                        -- io.put_string("Moving ")
                        -- io.put_int(y)
                        -- io.put_newline
                        moveto(y)
                end -- loop
                io.put_newline
                io.put_string("BUG touched every tile in the ")
                io.put_int(floor.length)
                io.put_string(" by ")
                io.put_int(floor.width)
                io.put_string(" room in ")
                io.put_int(move_counter)
                io.put_string(" moves.")
        end -- walk

        moveto(pos: INTEGER) is
        -- move the insect to the new position
        do
                inspect pos
                when 1 then insect.setxy(insect.xpos-1, insect.ypos-1)
                when 2 then insect.setxy(insect.xpos-1, insect.ypos)
                when 3 then insect.setxy(insect.xpos-1, insect.ypos+1)
                when 4 then insect.setxy(insect.xpos, insect.ypos-1)
                when 5 then insect.setxy(insect.xpos, insect.ypos+1)
                when 6 then insect.setxy(insect.xpos+1, insect.ypos-1)
                when 7 then insect.setxy(insect.xpos+1, insect.ypos)
                when 8 then insect.setxy(insect.xpos+1, insect.ypos+1)
                end -- inspect
                move_counter := move_counter + 1
                if (move_counter \\ 10) = 0 then
                   io.put_string(".")
                end
                -- io.put_string("- - -")
                -- floor.showroom
                -- insect.showpos
        end -- moveto

        hit is
        -- this tile has been touched by the bug: increment this tile
        local
                tile: INTEGER
        do
                tile := ((insect.xpos-1)*floor.width)+insect.ypos
                -- tile is where the bug is in the array
                -- io.put_string("Hitting array element ")
                -- io.put_int(tile)
                -- io.put_string(" ")
                -- io.put_int(insect.xpos)
                -- io.put_string(" ")
                -- io.put_int(insect.ypos)
                -- io.put_newline
                floor.floor.item(tile).hit
        end -- hit

end -- class WALK

                
