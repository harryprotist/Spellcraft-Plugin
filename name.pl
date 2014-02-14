#!/usr/bin/env perl
use warnings;
use strict;

use File::Slurp;

if (@ARGV != 3) {
	print "USAGE: name.pl NAMES FILE OUTPUT\n";
	exit 0;
}

my @names = grep { /\S/ } (split /\n/, read_file($ARGV[0]));
my @file = grep { /\S/ } (split /\n/, read_file($ARGV[1]));
open FILE, '>', $ARGV[2];
foreach (@file) {
	my @larr = split /\t/, $_;
	if ($larr[0] =~ /:/) {
		$larr[0] = (split /:/, $larr[0])[0];
	}
	my $rname = $names[$larr[0]];
	$larr[1] = $rname or $larr[1] . "(NAME SUBJECT TO CHANGE)";
	print FILE ((join " ", @larr[1 .. 2]) . "\n");	
}
print "Operation complete\n";
