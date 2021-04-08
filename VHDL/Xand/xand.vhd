library IEEE;
use IEEE.STD_LOGIC_1164.all;
use ieee.numeric_std;

entity xand is
	generic (width : integer := 8);
	port (
		clk : in std_logic;
		a, b : in std_logic_vector(width - 1 downto 0);
		x : out std_logic_vector(width - 1 downto 0)
	);
end xand;

architecture Behavioral of xand is
begin
	x <= (a and b) or not(a or b);
end Behavioral;
