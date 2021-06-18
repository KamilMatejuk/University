library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.numeric_std.all;

entity prescaler is
    generic (
        outNumber : natural := 2 -- how many outputs
    );
    port (
        regular_clk : in std_logic;
        rst : in std_logic;
        scaled_clk : out std_logic_vector(outNumber - 1 downto 0) := std_logic_vector(to_unsigned(0, outNumber))
    );
end prescaler;

architecture prescaler_arch of prescaler is

    signal counter : unsigned (outNumber - 1 downto 0) := to_unsigned(0, outNumber);

begin

    process (regular_clk, rst)
    begin
        if rst = '1' then
            counter <= to_unsigned(0, outNumber);
        elsif rising_edge(regular_clk) then
            if counter = 2 ** outNumber - 1 then
                counter <= to_unsigned(0, outNumber);
            else
                counter <= unsigned(counter + "1");
            end if;
        end if;
    end process;
    scaled_clk <= std_logic_vector(counter);

end prescaler_arch;
