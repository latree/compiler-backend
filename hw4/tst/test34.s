	.text
			  # _foo (i) (k)
			  # t1	%rax
			  # t2	%rdi
			  # t3	%rax
			  # t4	%rax
			  # i	%rdi
			  # k	%rbx
	.p2align 4,0x90
	.globl_foo
_foo:
	pushq %rbx
			  #  k = 10
	movq $10,%rbx
			  #  t1 = i > 0
	movq $0,%r10
	cmpq %r10,%rdi
	setg %al
	movzbq %al,%rax
			  #  if t1 == false goto L0
	movq $0,%r11
	cmpq %r11,%rax
	je foo_L0
			  #  t2 = call _bar(i)
	call _bar
	movq %rax,%rdi
			  #  t3 = call _foo(t2)
	call _foo
			  #  t4 = k + t3
	movq %rax,%r10
	movq %rbx,%rax
	addq %r10,%rax
			  #  k = t4
	movq %rax,%rbx
			  # L0:
foo_L0:
			  #  return k
	movq %rbx,%rax
	popq %rbx
	ret
			  # _bar (i) 
			  # t5	%rax
			  # i	%rdi
	.p2align 4,0x90
	.globl_bar
_bar:
	subq $8,%rsp
			  #  t5 = i - 1
	movq $1,%r10
	movq %rdi,%rax
	subq %r10,%rax
			  #  return t5
	addq $8,%rsp
	ret
			  # _main () 
			  # t6	%rdi
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t6 = call _foo(2)
	movq $2,%rdi
	call _foo
	movq %rax,%rdi
			  #  call _printInt(t6)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
