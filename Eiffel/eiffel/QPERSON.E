class QPERSON creation
    make
feature
    make is
    --create a QPERSON
    local
         per1,per2:PERSON
         q:AQUEUE[PERSON]
    do
      !!q.make(10)
      !!per1.make("Jimbo","Noplace",1999)
      !!per2.make("Bob the Plumber","24 Hiccup Street",1941)
      q.add(per1)
      q.add(per2)
      q.front.print
      q.remove
      q.front.print
    end--make
     end--class QPERSON

