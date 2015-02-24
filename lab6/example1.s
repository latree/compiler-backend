	.file	"example1.s"     # change this to match your filename
	.text
	.globl	f
f:
<<<<<<< HEAD
=======
        movl    $0, %eax        # initialize length count in eax
        jmp     test
loop:   incl    %eax            # increment count
        addq    $4, %rdi        # and move to next array element

test:   movl    (%rdi), %ecx    
        cmpl    $0, %ecx        
        jne     loop            # repeat if we're not done ...


>>>>>>> 89073945568adf93d0ad8bb16f75a407534360f7
	movl $0, %eax  		# initialize cnt = 0
L:	
	movl (%rdi), %ecx  	# load array element
	cmpl $0, %ecx		# test for end of array	
	je L2          		# if (a[cnt]==0) then exit loop
	incl %eax		# cnt++
	addq $4, %rdi		# move to next array element
	jmp L 			# repeat
L2:	
	# return cnt

	ret
