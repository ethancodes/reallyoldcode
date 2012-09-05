class stacktst creation
	create
feature
	create is
		-- tests class ASTACK
	local
		s: ASTACK[INTEGER]
                io: BASIC_IO
	do
                !!io
		!!s.create
		s.display
		s.push(1)
		s.display
		s.push(2)
		s.push(3)
		s.push(5)
		s.display
                io.put_string("The top element is ")
                io.put_int(s.top)
                io.put_newline
		s.pop		-- 5
		s.display
		s.push(4)
		s.display
                io.put_string("The length is ")
                io.put_int(s.length)
                io.put_newline
		s.pop		-- 4
		s.pop		-- 3
		s.pop		-- 2
		s.pop		-- 1
                s.display
	end -- create
end -- class stacktst

