require 'tesseract'
 
e = Tesseract::Engine.new {|e|
  e.language = :eng
  e.blacklist = "|._-—=~[]{},ﬂ!>°ﬁ%&$#¥ *'\"'‘’”abcdefghijklmnopqrstuwvxz"
}
 
s = e.text_for(ARGV[0]).strip

puts s # => 'ABC'
