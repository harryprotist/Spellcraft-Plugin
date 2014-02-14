#!/usr/bin/env perl
use warnings;
use strict;

use File::Slurp;

my @lines = grep { /\S/ } (split /\n/, read_file($ARGV[0]));
my @olines;
foreach (@lines) {
	my @aref = (grep { /\S/ } (split /\t/, $_));
	$aref[2] *= 10;
	$olines[@olines] = \@aref;
}
@olines = sort {@{$a}[1] <=> @{$b}[1]} @olines;
open FILE, '>', $ARGV[1];
foreach (@olines) {
	print FILE ((join "\t", @{$_}) . "\n");
}	
close FILE;
print "Operation completed\n";
