	.text
			  # _go (i, j, k) 
			  # t1	%rax
			  # t2	%rax
			  # i	%rdi
			  # j	%rsi
			  # k	%rdx
	.p2align 4,0x90
	.globl_go
_go:
	subq $8,%rsp
			  #  t1 = i + j
	movq %rdi,%rax
	addq %rsi,%rax
			  #  t2 = t1 + k
	addq %rdx,%rax
			  #  return t2
	addq $8,%rsp
	ret
			  # _main () 
			  # t3	%rdi
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t3 = call _go(1, 2, 3)
	movq $1,%rdi
	movq $2,%rsi
	movq $3,%rdx
	call _go
	movq %rax,%rdi
			  #  call _printInt(t3)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
