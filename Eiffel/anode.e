class ANODE creation
	create
feature
        item: integer
        next: ANODE[integer]

        create(i: integer) is
	do
		item := i
	end -- create

        link(new :ANODE[integer]) is
        do
                next := new
        end -- link
end -- class ANODE
