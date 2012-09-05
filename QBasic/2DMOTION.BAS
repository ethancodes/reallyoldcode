SCREEN 13
ncircles = 3
DIM circles(ncircles, 7)
FOR i = 1 TO ncircles
  RANDOMIZE TIMER
  circles(i, 1) = RND * 320
  circles(i, 2) = RND * 200
  circles(i, 3) = (RND * 10) + 1
  circles(i, 7) = (RND * 4) + 1
  'circles(i, 4) = (RND * 4) + 1
  circles(i, 4) = 15
  circles(i, 5) = circles(i, 1)
  circles(i, 6) = circles(i, 2)
NEXT i
r = 5
DO WHILE INKEY$ <> "q"
  LOCATE 11, (40 - 20) / 2
  PRINT "2-Dimensional Motion"
 
  FOR j = 1 TO ncircles
        CIRCLE (circles(j, 5), circles(j, 6)), r, 0
        IF (circles(j, 1) + r) > 319 OR (circles(j, 1) - r) < 1 THEN
        circles(j, 3) = circles(j, 3) * -1
        END IF
        IF (circles(j, 2) + r) > 199 OR (circles(j, 2) - r) < 1 THEN
        circles(j, 7) = circles(j, 7) * -1
        END IF
        circles(j, 5) = circles(j, 1)
        circles(j, 6) = circles(j, 2)
        circles(j, 1) = circles(j, 1) + circles(j, 3)
        circles(j, 2) = circles(j, 2) + circles(j, 7)
        CIRCLE (circles(j, 5), circles(j, 6)), r, circles(j, 4)
  NEXT j
LOOP




