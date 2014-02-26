#!/usr/bin/env perl
use strict;
use warnings;

use File::Slurp;

my @lines = read_file('spell.txt');
open FILE, '>', 'spell_n.txt';

my $i = 1;
foreach (@lines) {
	$_ =~ s/([^ ]+) (\d+) (\d+)/$1 $i $3/g;
	print FILE $_;
	$i++;
}
print "operation complete\n";
