package caccia.david.inthewind.impl;

import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StringWinderTest
{
    StringWinder sw = new StringWinder();

    @Test
    public void TwoFactorRoundTripTest()
    {
        int factorCount = 2;
        String message = "hello world.";

        List<String> factors = sw.unwind(message, factorCount);
        String messageOut = sw.wind(factors); // factors in original order

        assertThat(factors).hasSize(factorCount);
        assertThat(message).isEqualTo(messageOut);
    }

    @Test
    public void TwoFactorCommunteRoundTripTest()
    {
        int factorCount = 2;
        String message = "hello world.";

        List<String> factors = sw.unwind(message, factorCount);
        // Let's change the order of the factors
        String factor = factors.remove(0);
        factors.add(factor);
        String messageWithCommutedFactors = sw.wind(factors);
        assertThat(factors).hasSize(factorCount);
        assertThat(message).isEqualTo(messageWithCommutedFactors);
    }

    @Test
    public void ThreeFactorRoundTripTest()
    {
        int factorCount = 3;
        String message = "hello world.";

        List<String> factors = sw.unwind(message, factorCount);
        String messageOut = sw.wind(factors); // factors in original order

        assertThat(factors).hasSize(factorCount);
        assertThat(message).isEqualTo(messageOut);
    }

    @Test
    public void ThreeFactorCommunteRoundTripTest()
    {
        int factorCount = 3;
        String message = "hello world.";

        List<String> factors = sw.unwind(message, factorCount);
        // Let's change the order of the factors
        String factor = factors.remove(0);
        factors.add(factor);
        String messageWithCommutedFactors = sw.wind(factors);
        assertThat(factors).hasSize(factorCount);
        assertThat(message).isEqualTo(messageWithCommutedFactors);
    }

    @Test
    public void factorExpansionTest()
    {
        int factorCount = 2;
        String message = "hello world.";

        // encode two factors
        List<String> factors = sw.unwind(message, factorCount);

        // expand to three factors
        String factor = factors.remove(0);
        factors.addAll(sw.unwind(factor,factorCount));

        String messageOut = sw.wind(factors); // expanded factors

        assertThat(factors).hasSize(factorCount + 1);
        assertThat(message).isEqualTo(messageOut);
    }

    @Test
    public void factorConsolidationTest()
    {
        int factorCount = 3;
        String message = "hello world.";

        // encode two factors
        List<String> factors = sw.unwind(message, factorCount);

        // consolidate to two factors
        List<String> factorsToConsolidate = new LinkedList<>();
        factorsToConsolidate.add(factors.remove(0));
        factorsToConsolidate.add(factors.remove(0));
        factors.add(sw.wind(factorsToConsolidate));

        String messageOut = sw.wind(factors); // expanded factors

        assertThat(factors).hasSize(factorCount - 1);
        assertThat(message).isEqualTo(messageOut);
    }

    // Note that factor replacement requires at least two factors be replaced
    @Test
    public void factorReplacementTest()
    {
        int factorCount = 3;
        String message = "hello world.";

        // encode two factors
        List<String> factors = sw.unwind(message, factorCount);

        // consolidate to two factors
        List<String> factorsToConsolidate = new LinkedList<>();
        factorsToConsolidate.add(factors.remove(0));
        factorsToConsolidate.add(factors.remove(0));
        String intermediateMessage = sw.wind(factorsToConsolidate);
        List<String> replacementFactors = sw.unwind(intermediateMessage, 2);
        factors.addAll(replacementFactors);

        String messageOut = sw.wind(factors); // expanded factors

        assertThat(factors).hasSize(factorCount);
        assertThat(message).isEqualTo(messageOut);
        for(String factor: factors)
        {
            assertThat(factor).isNotEqualTo(factorsToConsolidate.get(0));
            assertThat(factor).isNotEqualTo(factorsToConsolidate.get(1));
        }
    }

    @Test
    public void parallelFactorsTest()
    {
        int factorCount = 3;
        int factorCountTwo = 2;
        String message = "hello world.";

        // encode factors
        List<String> factors = sw.unwind(message, factorCount);

        List<String> factorsTwo = sw.unwind(message, factorCountTwo);

        String messageOutThree = sw.wind(factors);
        String messageOutTwo = sw.wind(factorsTwo);
        assertThat(factors).hasSize(factorCount);
        assertThat(factorsTwo).hasSize(factorCountTwo);
        assertThat(message).isEqualTo(messageOutThree);
        assertThat(message).isEqualTo(messageOutTwo);
    }

}