	.text
			  # _go () (i, j)
			  # t1	%rax
			  # i	%rax
			  # j	%rax
	.p2align 4,0x90
	.globl_go
_go:
	subq $8,%rsp
			  #  i = 4
	movq $4,%rax
			  #  t1 = i + 2
	movq $2,%r10
	addq %r10,%rax
			  #  j = t1
			  #  return j
	addq $8,%rsp
	ret
			  # _main () (r)
			  # t2	%rax
			  # r	%rdi
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t2 = call _go()
	call _go
			  #  r = t2
	movq %rax,%rdi
			  #  call _printInt(r)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
