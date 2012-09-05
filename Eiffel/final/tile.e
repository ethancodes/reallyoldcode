class TILE
        creation make
feature
        hits: INTEGER

        make is
        -- create a tile
        do
                hits := 0
        end -- make

        hit is
        -- the tile has been hit, increment the counter
        do
                hits := hits + 1
        end -- hit
end -- class TILE
