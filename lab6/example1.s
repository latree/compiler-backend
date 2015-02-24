	.file	"example1.s"     # change this to match your filename
	.text
	.globl	f
f:
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
