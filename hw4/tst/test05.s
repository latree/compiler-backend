	.text
			  # _main () (r)
			  # t1	%rax
			  # r	%rdi
			  # t2	%rax
			  # t3	%rax
			  # t4	%rax
			  # t5	%rax
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  r = 1
	movq $1,%rdi
			  #  t1 = r
	movq %rdi,%rax
			  #  t2 = t1 + r
	addq %rdi,%rax
			  #  t3 = t2 + r
	addq %rdi,%rax
			  #  t4 = t3 + r
	addq %rdi,%rax
			  #  t5 = t4 + r
	addq %rdi,%rax
			  #  t6 = t5 + r
			  #  r = t5 + r
	movq %rdi,%r10
	movq %rax,%rdi
	addq %r10,%rdi
			  #  call _printInt(r)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
