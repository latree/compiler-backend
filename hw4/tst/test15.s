	.text
			  # _foo (i) (x)
			  # i	%rax
	.p2align 4,0x90
	.globl_foo
_foo:
	subq $8,%rsp
	movq %rdi,%rax
			  #  return i
	addq $8,%rsp
	ret
			  # _bar (i) (x)
			  # x	%rax
	.p2align 4,0x90
	.globl_bar
_bar:
	subq $8,%rsp
			  #  x = 2
	movq $2,%rax
			  #  return x
	addq $8,%rsp
	ret
			  # _main () (i, j)
			  # t1	%rax
			  # t2	%rax
			  # i	%rbx
			  # j	%rbp
	.p2align 4,0x90
	.globl_main
_main:
	pushq %rbx
	pushq %rbp
	subq $8,%rsp
			  #  t1 = call _foo(1)
	movq $1,%rdi
	call _foo
			  #  i = t1
	movq %rax,%rbx
			  #  t2 = call _bar(1)
	movq $1,%rdi
	call _bar
			  #  j = t2
	movq %rax,%rbp
			  #  call _printInt(i)
	movq %rbx,%rdi
	call _printInt
			  #  call _printInt(j)
	movq %rbp,%rdi
	call _printInt
			  #  return 
	addq $8,%rsp
	popq %rbp
	popq %rbx
	ret
