//gentree.h

#ifndef _TREENODE_H
#define _TREENODE_H

  template <class T>
  class treenode {
		public:
				// access to children and parent
/*			virtual  void				left		(treenode<T> * tnode)	= 0;
			virtual  treenode<T> *  left 		()	const						= 0;
			virtual  void				right		(treenode<T> * tnode)	= 0;
			virtual	treenode<T> *  right		()	const						= 0;
*/			virtual	int				find		(T item)						= 0;
			virtual	int 				size 		() const						= 0;

				// pure virtual methods
/*			virtual 	treenode<T> *	add		(T value)  					= 0;
			virtual	treenode<T> *	remove	(T , treenode<T> * &) 	= 0;
			virtual	treenode<T>	*	copy		(treenode<T> * ) const	= 0;
*/			virtual	void			   release	()								= 0;

	};

	// Declare an abstract Tree class

	template <class T>
	class Tree	{
		public:
				// virtual methods
			virtual	void		add				(T	value)			= 0;
			virtual	void		deleteAll		() 					= 0;
			virtual	int		includes			(T value) const	= 0;
			virtual	void		remove			(T value)			= 0;
			virtual	int		empty				() const				= 0;
	};


#endif
