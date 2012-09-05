class ASTACK creation
	create
feature
        front: ANODE[integer]      -- the front of the stack
        back: ANODE[integer]       -- because i can't call it top
        size: INTEGER              -- the size of the stack

	create is
		-- the creation procedure
	do
		size := 0
	end -- create

        push(i: integer) is
                -- push an integer onto the stack
	local
                new: ANODE[integer]
	do
		!!new.create(i)
		if empty then
			front := new
                else
                        back.link(new)
		end
                back := new
		size := size + 1
	end -- push

	pop is
                -- pop an integer off the top of the stack
	local
                i: ANODE[integer]
		io: BASIC_IO
	do	
		!!io
                if empty then
                        io.put_string("Cannot Pop. Empty Stack.")
                        io.put_newline
                else
			from i := front
                        until i.next = back
			loop
				i := i.next
			end
			back := i
                        back.link(void)
			size := size - 1
                end
	end -- pop

	empty: BOOLEAN is
		-- is the stack empty?
	do
		result := size=0
	end -- empty

        length: INTEGER is
                -- what is the length of the stack?
        do
                result := size
        end -- length
		
        top: integer is
                -- what's the top integer on the stack
	do
		result := back.item
	end -- top
end -- class ASTACK
