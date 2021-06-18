library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.numeric_std.all;

-- https://electronics.stackexchange.com/questions/61422/how-to-divide-50mhz-down-to-2hz-in-vhdl-on-xilinx-fpga

entity prescaler is
    generic (
        scale : natural := 1; -- hwo much slower should scaled_clk be
        NBit : natural := 8 -- how many bits to write scale
    );
    port (
        regular_clk : in std_logic;
        rst : in std_logic;
        scaled_clk : out std_logic
    );
end prescaler;

architecture prescaler_arch of prescaler is

    signal counter : unsigned(NBit downto 0) := (others => '0');
    signal scaled_clk_temp : std_logic := '0';

begin

    process (regular_clk, rst)
    begin
        if rst = '1' then
            scaled_clk_temp <= '0';
            counter <= (others => '0');
        elsif rising_edge(regular_clk) then
            if counter = scale / 2 - 1 then
                counter <= (others => '0');
                scaled_clk_temp <= not scaled_clk_temp;
            else
                counter <= counter + 1;
            end if;
        end if;
    end process;
    scaled_clk <= scaled_clk_temp;

end prescaler_arch;
