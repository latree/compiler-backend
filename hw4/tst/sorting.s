	.text
			  # _selectionSort (A, count) (temp, i, j, k)
			  # t1	%rax
			  # A	%rdi
			  # t2	%rcx
			  # t3	%rsi
			  # t4	%rsi
			  # t5	%rsi
			  # t6	%rsi
			  # t7	%r8
			  # t8	%r8
			  # t9	%r8
			  # t10	%rsi
			  # t11	%rsi
			  # t12	%rcx
			  # t13	%rcx
			  # t14	%rcx
			  # t15	%rcx
			  # t16	%rsi
			  # t17	%rsi
			  # t18	%rsi
			  # t19	%rdx
			  # t20	%rdx
			  # t21	%rdx
			  # t22	%rdx
			  # t23	%rcx
			  # temp	%rcx
			  # count	%rsi
			  # i	%rax
			  # j	%rcx
			  # k	%rdx
	.p2align 4,0x90
	.globl_selectionSort
_selectionSort:
	subq $8,%rsp
			  #  t1 = count - 1
	movq $1,%r10
	movq %rsi,%rax
	subq %r10,%rax
			  #  i = t1
			  # L0:
selectionSort_L0:
			  #  t2 = i >= 0
	movq $0,%r10
	cmpq %r10,%rax
	setge %cl
	movzbq %cl,%rcx
			  #  if t2 == false goto L1
	movq $0,%r11
	cmpq %r11,%rcx
	je selectionSort_L1
			  #  j = 0
	movq $0,%rcx
			  #  k = 0
	movq $0,%rdx
			  # L2:
selectionSort_L2:
			  #  t3 = j <= i
	cmpq %rax,%rcx
	setle %sil
	movzbq %sil,%rsi
			  #  if t3 == false goto L3
	movq $0,%r11
	cmpq %r11,%rsi
	je selectionSort_L3
			  #  t4 = j * 4
	movq $4,%r10
	movq %rcx,%rsi
	imulq %r10,%rsi
			  #  t5 = A + t4
	movq %rsi,%r10
	movq %rdi,%rsi
	addq %r10,%rsi
			  #  t6 = [t5]
	movslq (%rsi),%rsi
			  #  t7 = k * 4
	movq $4,%r10
	movq %rdx,%r8
	imulq %r10,%r8
			  #  t8 = A + t7
	movq %r8,%r10
	movq %rdi,%r8
	addq %r10,%r8
			  #  t9 = [t8]
	movslq (%r8),%r8
			  #  t10 = t6 > t9
	cmpq %r8,%rsi
	setg %sil
	movzbq %sil,%rsi
			  #  if t10 == false goto L4
	movq $0,%r11
	cmpq %r11,%rsi
	je selectionSort_L4
			  #  k = j
	movq %rcx,%rdx
			  # L4:
selectionSort_L4:
			  #  t11 = j + 1
	movq $1,%r10
	movq %rcx,%rsi
	addq %r10,%rsi
			  #  j = t11
	movq %rsi,%rcx
			  #  goto L2
	jmp selectionSort_L2
			  # L3:
selectionSort_L3:
			  #  t12 = k != i
	cmpq %rax,%rdx
	setne %cl
	movzbq %cl,%rcx
			  #  if t12 == false goto L5
	movq $0,%r11
	cmpq %r11,%rcx
	je selectionSort_L5
			  #  t13 = k * 4
	movq $4,%r10
	movq %rdx,%rcx
	imulq %r10,%rcx
			  #  t14 = A + t13
	movq %rcx,%r10
	movq %rdi,%rcx
	addq %r10,%rcx
			  #  t15 = [t14]
	movslq (%rcx),%rcx
			  #  temp = t15
			  #  t16 = i * 4
	movq $4,%r10
	movq %rax,%rsi
	imulq %r10,%rsi
			  #  t17 = A + t16
	movq %rsi,%r10
	movq %rdi,%rsi
	addq %r10,%rsi
			  #  t18 = [t17]
	movslq (%rsi),%rsi
			  #  t19 = k * 4
	movq $4,%r10
	imulq %r10,%rdx
			  #  t20 = A + t19
	movq %rdx,%r10
	movq %rdi,%rdx
	addq %r10,%rdx
			  #  [t20] = t18
	movl %esi,(%rdx)
			  #  t21 = i * 4
	movq $4,%r10
	movq %rax,%rdx
	imulq %r10,%rdx
			  #  t22 = A + t21
	movq %rdx,%r10
	movq %rdi,%rdx
	addq %r10,%rdx
			  #  [t22] = temp
	movl %ecx,(%rdx)
			  # L5:
selectionSort_L5:
			  #  t23 = i - 1
	movq $1,%r10
	movq %rax,%rcx
	subq %r10,%rcx
			  #  i = t23
	movq %rcx,%rax
			  #  goto L0
	jmp selectionSort_L0
			  # L1:
selectionSort_L1:
			  #  return 
	addq $8,%rsp
	ret
			  # _main () (numbers, cnt)
			  # t32	%rax
			  # t33	%rdi
			  # t34	%rax
			  # numbers	%rbx
			  # cnt	%rbp
			  # t24	%rax
			  # t25	%rax
			  # t26	%rax
			  # t27	%rcx
			  # t28	%rcx
			  # t29	%rax
			  # t30	%rax
			  # t31	%rax
	.p2align 4,0x90
	.globl_main
_main:
	pushq %rbx
	pushq %rbp
	subq $8,%rsp
			  #  t24 = call _malloc(40)
	movq $40,%rdi
	call _malloc
			  #  numbers = t24
	movq %rax,%rbx
			  #  cnt = 0
	movq $0,%rbp
			  # L6:
main_L6:
			  #  t25 = cnt < 10
	movq $10,%r10
	cmpq %r10,%rbp
	setl %al
	movzbq %al,%rax
			  #  if t25 == false goto L7
	movq $0,%r11
	cmpq %r11,%rax
	je main_L7
			  #  t26 = 10 - cnt
	movq $10,%r11
	movq %r11,%rax
	subq %rbp,%rax
			  #  t27 = cnt * 4
	movq $4,%r10
	movq %rbp,%rcx
	imulq %r10,%rcx
			  #  t28 = numbers + t27
	movq %rcx,%r10
	movq %rbx,%rcx
	addq %r10,%rcx
			  #  [t28] = t26
	movl %eax,(%rcx)
			  #  t29 = cnt + 1
	movq $1,%r10
	movq %rbp,%rax
	addq %r10,%rax
			  #  cnt = t29
	movq %rax,%rbp
			  #  goto L6
	jmp main_L6
			  # L7:
main_L7:
			  #  call _selectionSort(numbers, cnt)
	movq %rbx,%rdi
	movq %rbp,%rsi
	call _selectionSort
			  #  call _printStr("Your numbers in sorted order are:")
	leaq _S0(%rip),%rdi
	call _printStr
			  #  cnt = 0
	movq $0,%rbp
			  # L8:
main_L8:
			  #  t30 = cnt < 10
	movq $10,%r10
	cmpq %r10,%rbp
	setl %al
	movzbq %al,%rax
			  #  if t30 == false goto L9
	movq $0,%r11
	cmpq %r11,%rax
	je main_L9
			  #  t31 = cnt * 4
	movq $4,%r10
	movq %rbp,%rax
	imulq %r10,%rax
			  #  t32 = numbers + t31
	movq %rax,%r10
	movq %rbx,%rax
	addq %r10,%rax
			  #  t33 = [t32]
	movslq (%rax),%rdi
			  #  call _printInt(t33)
	call _printInt
			  #  t34 = cnt + 1
	movq $1,%r10
	movq %rbp,%rax
	addq %r10,%rax
			  #  cnt = t34
	movq %rax,%rbp
			  #  goto L8
	jmp main_L8
			  # L9:
main_L9:
			  #  return 
	addq $8,%rsp
	popq %rbp
	popq %rbx
	ret
_S0:
	.asciz "Your numbers in sorted order are:"
