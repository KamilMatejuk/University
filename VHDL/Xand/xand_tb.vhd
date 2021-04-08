library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity xand_tb is
end xand_tb;

architecture behavior of xand_tb is

	-- deklaracja komponentu Unit Under Test (UUT)

	component xand
		generic (width : integer);
		port (
			clk : in std_logic;
			a : in std_logic_vector(width - 1 downto 0);
			b : in std_logic_vector(width - 1 downto 0);
			x : out std_logic_vector(width - 1 downto 0)
		);
	end component;

	signal width : integer := 8;

	-- inputs
	signal clk : std_logic := '0';
	signal a : std_logic_vector(width - 1 downto 0) := (others => '0');
	signal b : std_logic_vector(width - 1 downto 0) := (others => '0');

	-- outputs
	signal x : std_logic_vector(width - 1 downto 0);

	-- okres zegara
	constant period : time := 10 ns;

begin

	-- instantiate the Unit Under Test (UUT)
	uut : xand
	generic map(width => 8)
	port map(
		clk => clk,
		a => a,
		b => b,
		x => x
	);

	stim_proc : process
	begin
		-- hold reset state for 100 ns.
		wait for period * 10;

		a <= "00000000";
		b <= "00000000";
		wait for period;
		assert x = "11111111";

		a <= "11111111";
		b <= "11111111";
		wait for period;
		assert x = "11111111";

		a <= "01010101";
		b <= "01010101";
		wait for period;
		assert x = "11111111";

		a <= "01010101";
		b <= "10101010";
		wait for period;
		assert x = "00000000";

		a <= "11010111";
		b <= "00100010";
		wait for period;
		assert x = "00001010";

		report "End of test" severity note;

		wait;
	end process;
end;
